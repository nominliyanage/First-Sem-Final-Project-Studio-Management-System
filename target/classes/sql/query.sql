drop database if exists studio;
create database if not exists studio;

use studio;

CREATE TABLE User (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    password VARCHAR(50)
);


CREATE TABLE Employee (
    employee_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    tel VARCHAR(50),
    role VARCHAR(50),
    user_id VARCHAR(50),
    foreign key(user_id) references User(user_id) on update cascade on delete cascade
);

CREATE TABLE Client (
    client_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    tel VARCHAR(50),
    user_id VARCHAR(50),
    foreign key(user_id) references User(user_id) on update cascade on delete cascade
);


CREATE TABLE Equipment (
    equipment_id VARCHAR(50) PRIMARY KEY,
    equipment_name VARCHAR(50),
    purchase_date DATE,
    maintain_history VARCHAR(50)
);


CREATE TABLE Gallery (
    gallery_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    date DATE,
    description VARCHAR(50),
    client_id VARCHAR(50),
    foreign key(client_id) references Client(client_id) on update cascade on delete cascade
);


CREATE TABLE Feedback (
    feedback_id VARCHAR(50) PRIMARY KEY,
    client_id VARCHAR(50),
    description VARCHAR(50),
    foreign key(client_id) references Client(client_id) on update cascade on delete cascade
);


CREATE TABLE Salary (
    salary_id VARCHAR(50) PRIMARY KEY,
    employee_id VARCHAR(50),
    amount DECIMAL(10,2),
    date DATE,
    foreign key(employee_id) references Employee(employee_id) on update cascade on delete cascade
);

CREATE TABLE Package (
    package_id VARCHAR(50) PRIMARY KEY,
    package_name VARCHAR(50),
    price DECIMAL(10,2),
    includes VARCHAR(50)
);


CREATE TABLE Booking (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50),
    client_id VARCHAR(50),
    employee_id VARCHAR(50),
    package_id VARCHAR(50),
    date DATE,
    time TIME,
    location VARCHAR(50),
    price DECIMAL(10,2),
    foreign key(user_id) references User(user_id) on update cascade on delete cascade,
    foreign key(client_id) references Client(client_id) on update cascade on delete cascade,
    foreign key(employee_id) references Employee(employee_id) on update cascade on delete cascade,
    foreign key(package_id) references Package(package_id) on update cascade on delete cascade
);


CREATE TABLE Payment (
    payment_id VARCHAR(50) PRIMARY KEY,
    client_id VARCHAR(50),
    booking_id VARCHAR(50),
    amount DECIMAL(10,2),
    date DATE,
    foreign key(client_id) references Client(client_id) on update cascade on delete cascade,
    foreign key(booking_id) references Booking(booking_id) on update cascade on delete cascade 
);


CREATE TABLE Gallery_Details (
    user_id VARCHAR(50),
    gallery_id VARCHAR(50),
    foreign key(user_id) references User(user_id) on update cascade on delete cascade,
    foreign key(gallery_id) references Gallery(gallery_id) on update cascade on delete cascade
);


CREATE TABLE Equipment_Details (
    user_id VARCHAR(50),
    equipment_id VARCHAR(50),
    foreign key(user_id) references User(user_id) on update cascade on delete cascade,
    foreign key(equipment_id) references Equipment(equipment_id) on update cascade on delete cascade
);

CREATE TABLE Booking_Details (
    booking_id VARCHAR(50),
    package_id VARCHAR(50),
    date Date,
    foreign key(booking_id) references Booking(booking_id) on update cascade on delete cascade,
    foreign key(package_id) references Package(package_id) on update cascade on delete cascade
);



insert into user values("U001", "Nomin", "1234");

insert into client values("C001", "Dumindu", "0757894562", "U001");
insert into client values("C002", "Nimila", "0778945365", "U001");
insert into client values("C003", "Himantha", "0778529634", "U001");

insert into employee values("E001", "Nomin", "0758847325", "Main Photographer", "U001");
insert into employee values("E002", "Ravindu", "0778965475", "Photographer", "U001");
insert into employee values("E003", "Saranga", "0713216548", "Editor", "U001");

insert into equipment values("Eq001", "Canon 6D Camera", "2023-03-23", "Maintained");
insert into equipment values("Eq002", "Canon 50mm  lense", "2023-03-26", "Maintained");
insert into equipment values("Eq003", "Canon 85mm Art lense", "2023-08-10", "Maintained");

insert into gallery values("G001", "Wedding Shoot", "2024-05-02", "All retouched", "C001");
insert into gallery values("G002", "Wedding Shoot", "2024-05-05", "All retouched", "C002");

insert into salary values("S001", "E001", 75000.00, "2024-05-01");
insert into salary values("S002", "E002", 50000.00, "2024-05-01");
insert into salary values("S003", "E003", 45000.00, "2024-05-01");

insert into package values("P001", "Wedding Gold Package", 125000.00, "One enlargement and thanks card");
insert into package values("P002", "Wedding Diamond Package", 155000.00, "Three enlargements and thanks card");
insert into package values("P003", "Wedding Platinum Package", 185000.00, "Four enlargements and thanks card");