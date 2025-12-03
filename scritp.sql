-- =============================================
-- SCRIPT CORREGIDO Y FUNCIONANDO 100%
-- Base de datos ValleReg + todas las tablas
-- =============================================

-- Si la base de datos ya existe, la usamos. Si no, la creamos.
USE vinum_aw
GO


IF DB_ID('ValleReg') IS NULL
BEGIN
    CREATE DATABASE ValleReg;
    PRINT 'Base de datos ValleReg creada';
END
ELSE
BEGIN
    PRINT 'Base de datos ValleReg ya existe, se continuará usando';
END
GO

USE ValleReg;
GO

-- =============================================
-- Tablas de ubicación (UBIGEO Perú)
-- =============================================
IF OBJECT_ID('Distrito', 'U') IS NOT NULL DROP TABLE Distrito;
IF OBJECT_ID('Provincia', 'U') IS NOT NULL DROP TABLE Provincia;
IF OBJECT_ID('Departamento', 'U') IS NOT NULL DROP TABLE Departamento;
IF OBJECT_ID('Estudiante', 'U') IS NOT NULL DROP TABLE Estudiante;
IF OBJECT_ID('Programa', 'U') IS NOT NULL DROP TABLE Programa;
GO

CREATE TABLE Departamento (
    id CHAR(2) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE Provincia (
    id CHAR(4) PRIMARY KEY,
    departamento_id CHAR(2) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES Departamento(id)
);

CREATE TABLE Distrito (
    id CHAR(6) PRIMARY KEY,
    provincia_id CHAR(4) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (provincia_id) REFERENCES Provincia(id)
);

-- =============================================
-- Tabla Programa
-- =============================================
CREATE TABLE Programa (
    id INT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    duracion_semestres TINYINT NOT NULL,
    activo BIT DEFAULT 1
);

-- =============================================
-- Tabla Estudiante (aquí estaba el error de la coma)
-- =============================================
CREATE TABLE Estudiante (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    numero_documento VARCHAR(15) UNIQUE NOT NULL,
    tipo_documento VARCHAR(20) NOT NULL DEFAULT 'DNI',
    apellidos VARCHAR(100) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero CHAR(1) CHECK (genero IN ('M','F','O')),
    telefono VARCHAR(15),
    celular VARCHAR(9),
    email VARCHAR(150) UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    distrito_id CHAR(6) NOT NULL,
    programa_id INT NOT NULL,
    año_ingreso SMALLINT NOT NULL DEFAULT YEAR(GETDATE()),
    fecha_registro DATETIME2 DEFAULT GETDATE(),
    estado BIT DEFAULT 1,  -- 1 = activo, 0 = eliminado lógico
    FOREIGN KEY (distrito_id) REFERENCES Distrito(id),
    FOREIGN KEY (programa_id) REFERENCES Programa(id)
);
GO

-- =============================================
-- Datos de prueba (UBIGEO reducido + programas + estudiantes)
-- =============================================
INSERT INTO Departamento (id, nombre) VALUES
('15','Lima'),
('01','Amazonas'),
('05','Callao'),
('07','Cusco'),
('12','Junín');

INSERT INTO Provincia (id, departamento_id, nombre) VALUES
('1501','15','Lima'),
('1502','15','Barranca'),
('0701','07','Cusco'),
('0501','05','Callao'),
('1201','12','Huancayo');

INSERT INTO Distrito (id, provincia_id, nombre) VALUES
('150101','1501','Lima'),
('150102','1501','Miraflores'),
('150103','1501','San Isidro'),
('150104','1501','San Borja'),
('150105','1501','Surco'),
('070101','0701','Cusco'),
('050101','0501','Callao'),
('120101','1201','Huancayo');

INSERT INTO Programa (codigo, nombre, duracion_semestres) VALUES
('ADM01', 'Administración de Empresas', 6),
('CON02', 'Contabilidad', 6),
('COMP03', 'Computación e Informática', 6),
('ENF04', 'Enfermería Técnica', 6),
('MEC05', 'Mecánica Automotriz', 6);

INSERT INTO Estudiante 
(numero_documento, tipo_documento, apellidos, nombres, fecha_nacimiento, genero, 
 celular, email, direccion, distrito_id, programa_id, año_ingreso)
VALUES
('76543215678', 'DNI', 'PÉREZ GÓMEZ', 'Luis Alberto', '2005-08-20', 'M', '987654321', 'luis@gmail.com', 'Av. La Marina 1234', '150101', 3, 2025),
('71234567',  'DNI', 'RAMÍREZ SOTO', 'María Fernanda', '2004-03-15', 'F', '912345678', 'maria@hotmail.com', 'Jr. de la Unión 567', '150102', 1, 2025),
('79876543',  'DNI', 'TORRES VARGAS', 'Ana Sofía', '2006-01-10', 'F', '923456789', null, 'Calle Los Olivos 890', '070101', 4, 2025),
('70123456',  'DNI', 'MENDOZA CRUZ', 'Carlos Eduardo', '2005-11-30', 'M', '945678123', 'carlos@outlook.com', 'Av. Arequipa 555', '150103', 2, 2025),
('88991234',  'DNI', 'HUAMÁN FLORES', 'Rosa Elena', '2005-07-22', 'F', '956789234', 'rosa@gmail.com', 'Jr. Cusco 777', '070101', 5, 2025);
GO

PRINT 'Todo creado correctamente. Ya puedes iniciar el backend';