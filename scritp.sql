
USE master
GO


DROP DATABASE IF EXISTS diario_ciudadano;
GO
CREATE DATABASE diario_ciudadano;
GO
USE diario_ciudadano;
GO


---------------------------------------------------------
-- TABLA MAESTRA: corresponsal
-- Incluye 6 campos principales bien identificados
-- + 4 restricciones: PK / UNIQUE / DEFAULT / CHECK
---------------------------------------------------------
CREATE TABLE corresponsal(
	id INT IDENTITY(1,1) PRIMARY KEY,                      -- PK
	name VARCHAR(200) NOT NULL,
	surnames VARCHAR(150) NOT NULL,
	id_doc VARCHAR(30) NOT NULL UNIQUE,                    -- UNIQUE
	country VARCHAR(100) NOT NULL,
	department VARCHAR(100) NOT NULL,
	province VARCHAR(100) NOT NULL,
	district VARCHAR(100) NOT NULL,
	locality VARCHAR(150) NOT NULL,

	status BIT NOT NULL DEFAULT 1,                         -- DEFAULT
	register_day DATETIME NOT NULL DEFAULT GETDATE(),

	-- CHECK: expresión lógica (solo valores 0 o 1)
	CONSTRAINT chk_status_corresponsal CHECK (status IN (0,1))
);
GO


---------------------------------------------------------
-- TABLA noticia (DETALLE 1)
---------------------------------------------------------
CREATE TABLE noticia (
    id_noticia INT IDENTITY(1,1) PRIMARY KEY,
    id_corresponsal INT NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    contenido VARCHAR(MAX) NOT NULL,
    video_url VARCHAR(500),
    fecha_publicacion DATETIME NOT NULL DEFAULT GETDATE(),
    status BIT NOT NULL DEFAULT 1,

    -- FK corregida: el campo es id_corresponsal
    CONSTRAINT fk_noticia_corresponsal
        FOREIGN KEY (id_corresponsal)
        REFERENCES corresponsal(id)
        ON DELETE CASCADE
);
GO


CREATE TABLE ubigeo (
    id INT IDENTITY(1,1) PRIMARY KEY,                    -- RESTRICCIÓN 1: PK
    codigo CHAR(6) NOT NULL UNIQUE,                      -- RESTRICCIÓN 2: UNIQUE (ubigeo oficial)
    departamento VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    distrito VARCHAR(150) NOT NULL,
    status BIT NOT NULL DEFAULT 1,                       -- RESTRICCIÓN 3: DEFAULT 1
    register_day DATETIME NOT NULL DEFAULT GETDATE(),    -- Fecha de registro automático
    
    -- RESTRICCIÓN 4: CHECK - Expresión lógica (solo permite 0 o 1)
    CONSTRAINT chk_status_ubigeo CHECK (status IN (0, 1))
);
GO


---------------------------------------------------------
-- TABLA foto_noticia (DETALLE 2)
---------------------------------------------------------
CREATE TABLE foto_noticia (
    id_foto INT IDENTITY(1,1) PRIMARY KEY,
    id_noticia INT NOT NULL,
    ruta_foto VARCHAR(500) NOT NULL,

    CONSTRAINT fk_foto_noticia
        FOREIGN KEY (id_noticia)
        REFERENCES noticia(id_noticia)
        ON DELETE CASCADE
);
GO


INSERT INTO corresponsal(name, surnames, id_doc, country, department, province, district, locality, status, register_day) VALUES
('Pedro','Soto Lara', '73144231', 'Perú', 'Lima', 'Cañete','San Vicente de Cañete', 'Jr. Grau', 1,  GETDATE()),
('Juana','Quispe Mamani', '73166542', 'Perú', 'Lima', 'Cañete','San Vicente de Cañete', 'Jr. Bolognesi', 1,  GETDATE()),
('Pablo','Huaco Ramirez', '73299876', 'Perú', 'Lima', 'Cañete','San Vicente de Cañete', 'Mr. Benavides', 0,  GETDATE()),
('Lukas','Cárdenas Gonzales', '76155692', 'Perú', 'Lima', 'Cañete','San Vicente de Cañete', 'Mercadillo', 0,  GETDATE());



INSERT INTO noticia(id_corresponsal,titulo,contenido, video_url, fecha_publicacion, status) VALUES
('1','Ataque Cibernetico a Municipalidad de Cañete', 'Ataque cibernetico en la Provincia de Cañete, la seguridad cibernetica debe ser el dia a dia de los Ingenieros de Sistemas','https://www.youtube.com/watch?v=o7DpYs3o38o&pp=ugUEEgJlc9IHCQkiCgGHKiGM7w%3D%3D', GETDATE(), 1);



INSERT INTO ubigeo (codigo, departamento, provincia, distrito, status) VALUES
('150501', 'Lima', 'Cañete', 'San Vicente de Cañete', 1),    -- Capital de la provincia
('150502', 'Lima', 'Cañete', 'Asia', 1),
('150503', 'Lima', 'Cañete', 'Calango', 1),
('150504', 'Lima', 'Cañete', 'Cerro Azul', 1),
('150505', 'Lima', 'Cañete', 'Chilca', 1),
('150506', 'Lima', 'Cañete', 'Coayllo', 1),
('150507', 'Lima', 'Cañete', 'Imperial', 1),
('150508', 'Lima', 'Cañete', 'Lunahuaná', 1),
('150509', 'Lima', 'Cañete', 'Mala', 1),
('150510', 'Lima', 'Cañete', 'Nuevo Imperial', 1),
('150511', 'Lima', 'Cañete', 'Pacarán', 1),
('150512', 'Lima', 'Cañete', 'Quilmaná', 1),
('150513', 'Lima', 'Cañete', 'San Antonio', 1),
('150514', 'Lima', 'Cañete', 'San Luis', 1),
('150515', 'Lima', 'Cañete', 'Santa Cruz de Flores', 1),
('150516', 'Lima', 'Cañete', 'Zúñiga', 1);
GO




SELECT * FROM corresponsal;
SELECT name, surnames FROM corresponsal;
SELECT * FROM corresponsal WHERE status = 1;
SELECT * FROM corresponsal ORDER BY register_day DESC;
GO


UPDATE corresponsal SET name = 'Juan' WHERE id = 1;
UPDATE corresponsal SET status= 1 WHERE id = 3;
UPDATE corresponsal SET country = 'Colombia' WHERE id = 1;





SELECT * FROM noticia;
SELECT titulo, contenido, fecha_publicacion FROM noticia;
SELECT * FROM noticia WHERE status = 1;
GO

UPDATE noticia SET status = 0 WHERE id_noticia = 1;


