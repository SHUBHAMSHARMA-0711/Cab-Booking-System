use cab2;
show triggers;


SELECT type,status, COUNT(*) AS payment_count, 
SUM(amount) AS payment_total, ( SELECT COUNT(DISTINCT id) 
FROM payment p2 WHERE p2.type = payment.type AND p2.status = payment.status )
 AS unique_customers FROM payment GROUP BY type, status WITH ROLLUP;


select type,status, sum(amount) from payment group by type ,status with rollup;
select coalesce(type,'Total'), sum(amount) from payment where status = 'success' group by (type) with rollup;

Select t.custId, t.maxPrice, customer.firstName 
from customer inner join (select custId, max(price) as maxPrice 
from booking where status = 'success'
group by (custId) with rollup) as t on customer.id = t.custId;


select count(*) as Count ,tripRating,c.id from customer as c 
inner join (select b.*,t.tripRating from booking as b 
inner join trip as t on b.id = t.bookingId ) as t1 on t1.custId=c.id 
group by tripRating,id with rollup;  


select count(*) as NumberOfReports, custId from booking as b
inner join report as t on b.id = t.bookingId 
group by custId with rollup order by count(*) desc;


Select type,SUM(Total) as ActualTotal , SUM(PriceT) as Potential, (SUM(PriceT) - SUM(Total)) as NetPotentialLoss 
from (SELECT coalesce(type,'Sub Total 1') as type, SUM(amount) as Total, SUM(price) as PriceT
FROM  payment p1 inner join booking where booking.id =(Select bookingId from trip where paymentId = p1.id )
GROUP BY type with rollup
UNION ALL
SELECT coalesce(p2.status,'Sub Total 2') as type, SUM(amount) as Total , SUM(price) as PriceT
FROM payment p2 inner join booking where booking.id =(Select bookingId from trip where paymentId = p2.id ) 
GROUP BY p2.status with rollup)
as t group by t.type with rollup;



select count(*) as Count ,tripRating,c.id from customer as c 
inner join (select b.*,t.tripRating from booking as b 
left join trip as t on b.id = t.bookingId union select b.*,t.tripRating from booking as b 
right join trip as t on b.id = t.bookingId ) as t1 on t1.custId=c.id 
group by tripRating,id with rollup;  


Select type,SUM(Total)from (SELECT coalesce(type,'Sub Total 1') as type, SUM(amount) as Total 
FROM  payment GROUP BY type with rollup
UNION ALL
SELECT coalesce(status,'Sub Total 2') as type, SUM(amount) as Total 
FROM  payment GROUP BY status with rollup) as t group by t.type with rollup;





DELIMITER //
Create trigger TripChecker2
before insert on trip for each row
BEGIN
   DECLARE tripstatus varchar(20);
   IF (NEW.netDistance) < 0 OR (NEW.completion<0) THEN
      SET NEW.status= 'aborted';
   ELSEIF (NEW.completion>=95) THEN
      SET NEW.status = 'success';
   ELSE
      SET NEW.status = 'started';
   END IF;
   IF NEW.completion >100 THEN SET NEW.completion =100;
   END IF;
   IF NEW.netDistance <0 THEN SET NEW.netDistance =0;
   END IF;
   IF NEW.completion <0 THEN SET NEW.completion =0;
   END IF;
   IF NEW.endTime <= (Select bookingTime from booking where id = NEW.bookingId)THEN
   SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'Please enter valid Trip End Time';
   END IF;
END//
DELIMITER ;



DELIMITER //
Create trigger driverAssigner
before insert on booking for each row
BEGIN
   DECLARE driverId int;
   DECLARE Rc int;
   DECLARE carType varchar(20);
   Select id into driverId from driver where status = 'active' limit 1;
   Select driver.RC into Rc from driver where id = driverId;
   Select car.type into cartype from car where id = Rc;
   IF carType = 'Sedan' THEN
      SET NEW.price = 1000 ;
   ELSEIF carType = 'SUV' THEN
       SET NEW.price = 1200 ;
   ELSEIF carType = 'Micro' THEN
       SET NEW.price = 500 ;
   ELSE
       SET NEW.price = 800 ;
   END IF;
   Update driver set status ='busy' where id = driverId;
   SET NEW.driverId=driverId;
   SET NEW.RC=Rc;
   SET NEW.status='waiting';
END//
DELIMITER ;

show triggers;
drop trigger tripChecker2;

CREATE TABLE driver_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    DriverDL varchar(100) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    oldRC int NOT NULL,
    changedate DATETIME DEFAULT NULL,
    action VARCHAR(50) DEFAULT NULL
);
drop table driver_history;
desc driver_history;

DELIMITER //
Create trigger driverUpdateHistory
BEFORE UPDATE ON driver
for each row
Insert into driver_history set DriverDL = OLD.DLNumber, name = OLD.lastname, oldRC = OLD.RC, changedate =NOW();

DELIMITER ;

drop trigger T1;



CREATE TABLE wallet_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customerId int NOT NULL,
    oldMoney int NOT NULL,
    changedate DATETIME DEFAULT NULL,
    action VARCHAR(50) DEFAULT NULL
);

CREATE TABLE user_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customerId int NOT NULL,
    name varchar(50) NOT NULL,
    username varchar(50) NOT NULL,
    changedate DATETIME DEFAULT NULL,
    action VARCHAR(50) DEFAULT NULL
);

Create trigger userHistorty
AFTER DELETE ON customer for each row
Insert into user_history set action = 'DELETED', customerId = OLD.id, name = OLD.name,username = OLD.username, changedate =NOW();

Create trigger walletHistory
BEFORE UPDATE ON wallet for each row
Insert into wallet_history set action = 'UPDATED', customerId = OLD.id, oldMoney = OLD.money, changedate =NOW();



delimiter //
CREATE TRIGGER person_age BEFORE INSERT
ON customer
FOR EACH ROW
IF NEW.age > 120 or NEW.age <8 THEN
SIGNAL SQLSTATE '50001' SET MESSAGE_TEXT = 'Please enter valid age';
END IF; //
delimiter ;
