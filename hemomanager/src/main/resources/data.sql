insert into officer
(uuid, name, cpf_cnpj, email, password, logged, admin, role_officer)
values
('9ded25f6-3bb3-4844-baad-89ab2c2feda1', 'José da Silva', '83343222836', 'dromanov@crossfitcoastal.com', 'O%9l22%EUxgV', false, false, 'nurse'),
('6a194a0a-e224-459c-aac9-69cdac800db6', 'Maria do Socorro', '40207360812', 'iborisenkov@disipulo.com', 'zrL2A7E^7W6k', false, false, 'receptionist'),
('e1cf9521-ac4e-4d25-8b2c-243b173c6978', 'Marcelo Rezende', '13344538888', 'massive2003@ic-osiosopra.it', '^^FP031H7jzp', false, true, 'manager');

insert into donor
(uuid, name, cpf_cnpj, email, password, logged, birth_date, sex, blood_type, phone, valid_donor, zip_code, home_number)
values
('cfbe5ae7-e25e-4e46-8ffe-24f6e686137b', 'Friduman Lena', '23098528800', 'massive2003@ic-osiosopra.it', 'oAGTr##13O35', false, '2001-07-12', 'F', 'A-', '(11) 912345678', true, '12402350', '456'),
('6b1902be-a57f-487e-b753-0ad6a7ea0d03', 'Radovan Anayeli', '59193480890', 'chechorico@honghukangho.com', '1qjV8Q*g8Sh0', false, '2002-11-07', 'M', 'B+', '(11) 987654321', false, '06717971', '842'),
('a6b9cb1a-bd72-491b-9b86-1fe2f24eae24', 'Kristaps Rasim', '79108106878', 'stassypryn@btcmod.com', '3R#jkBH2%Fy6', false, '2003-05-13', 'F', 'O-', '(11) 945612378', true, '03415010', '268');