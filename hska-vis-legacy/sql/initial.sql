USE webshop;

insert into webshop.role (`level1`, `type`) values(0, 'admin');
insert into webshop.role (`level1`, `type`) values(1, 'user');

insert into webshop.customer (`name`, `lastname`, `password`, `username`, `role`) values('admin', 'admin', 'admin', 'admin', 1);
