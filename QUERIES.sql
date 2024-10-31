use cab2;
desc trip;

select * from booking where custId = 1004 order by bookingTime;
select * from booking where status = 'waiting';
select (issue) from report where bookingId =10005;
select * from booking where driverId = 104 order by bookingTime;
select * from customer where id=1001;

select * from car where status='active' AND location='SouthDelhi';
select * from wallet where id =1001;
select count(*) as TotalCars,type from car group by type;
select count(*) as TotalCars,status,type from car where status = 'notActive' group by type;
select custId,count(*) as TotalBooking from booking group by custId having count(*) >3;

select * from trip where bookingId in (select (id) from booking where custId=1004 AND status='success');
select sum(amount) from payment where id in ( select paymentId from trip where bookingId in 
(select id from booking where driverId=104 AND status = 'success'));

select amount from payment where id in (select paymentId from trip where bookingId in 
(select id from booking where custId=1004));

select * from driver where id in (select distinct driverId from booking); 
select firstName, DLNumber from driver where rc in (select id from car); 
select * from car where id not in (select rc from driver); 
select id,rc from driver where rc in(select id from car where status='active' AND location='SouthDelhi');
select id,rc,status from driver where rc in(select id from car where status='active' AND type='Sedan');
select issue from report where bookingId in (select id from booking where custId =1004); 

select * from customer left join wallet on customer.id = wallet.id; 
select distinct * from customer where id in(select custId from booking where id in (select bookingId from report)); 
select * from trip where bookingId in(select id from booking where custId =1004 );
select * from trip left join payment on payment.id=trip.paymentId where bookingId in(select id from booking where custId =1004 );

select count(*) from trip where bookingId in(select id from booking where custId =1004 );
select * from booking join trip on booking.id = trip.bookingId where booking.custId = 1004;
select * from trip left join payment on trip.paymentId = payment.id where trip.status = 'success';

select * from payment where amount between 6000 and 7000 order by amount desc;  
SELECT * FROM car WHERE location LIKE '%SouthDelhi%' and status='active';

desc car;


select CONCAT(firstName, ' ', lastName) AS NAME from customer;

select id from customer where id not in( select distinct custId from booking);	

Update customer set firstName = 'Shubham' where id = 1001;
Update trip set startTime='2024-01-01 09:45:00',endTime='2024-01-01 11:45:00' where id%2!=0;

delete from customer where id = 1001;
desc booking;

alter table wallet add column debitCard int;
alter table wallet drop column debitCard;
alter table customer modify column altPhoneNumber int default null unique;


INSERT INTO `trip` (`id`, `bookingId`, `startTime`, `endTime`, `completion`, `status`, `paymentId`, `tripRating`, `netDistance`)
VALUES (200, 10222, '2024-02-02 01:36:38', '2024-02-02 16:21:48', 99, 'success', 62, 5, 20);
