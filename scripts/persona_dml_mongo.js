db = db.getSiblingDB('persona_db');

db.persona.insertMany([
	{
		"_id": NumberInt(123456789),
		"nombre": "Pepe",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(987654321),
		"nombre": "Pepito",
		"apellido": "Perez",
		"genero": "M",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(321654987),
		"nombre": "Pepa",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(147258369),
		"nombre": "Pepita",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(10),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(963852741),
		"nombre": "Fede",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(18),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	}
], { ordered: false });

db.profesion.insertMany([
	{
		"_id": NumberInt(1),
		"nom": "Ingeniero de Sistemas",
		"des": "Desarrollo de software y sistemas",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
	},
	{
		"_id": NumberInt(2),
		"nom": "Médico",
		"des": "Profesional de la salud",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
	},
	{
		"_id": NumberInt(3),
		"nom": "Abogado",
		"des": "Profesional del derecho",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument"
	}
], { ordered: false });

db.telefono.insertMany([
	{
		"_id": "3001234567",
		"oper": "Claro",
		"duenio": NumberInt(123456789),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
	},
	{
		"_id": "3109876543",
		"oper": "Movistar",
		"duenio": NumberInt(987654321),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
	}
], { ordered: false });

db.estudios.insertMany([
	{
		"_id": ObjectId(),
		"primaryPersona": NumberInt(123456789),
		"primaryProfesion": NumberInt(1),
		"fecha": new Date("2020-01-15"),
		"univer": "Pontificia Universidad Javeriana",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
	},
	{
		"_id": ObjectId(),
		"primaryPersona": NumberInt(987654321),
		"primaryProfesion": NumberInt(2),
		"fecha": new Date("2018-06-20"),
		"univer": "Universidad Nacional",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument"
	}
], { ordered: false });

print("✅ Datos iniciales cargados correctamente");
