#!/bin/bash

echo "======================================"
echo "Testing Profession & Persona Endpoints"
echo "======================================"
echo ""

echo "1. Listar profesiones en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/maria' -H 'accept: application/json' | jq
echo -e "\n"

echo "2. Listar profesiones en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/mongo' -H 'accept: application/json' | jq
echo -e "\n"

echo "3. Crear profesión en MariaDB:"
curl -s -X POST 'http://localhost:3000/api/v1/profession/maria' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "100",
  "name": "Test Engineer Maria",
  "description": "Profesión de prueba en MariaDB"
}' | jq
echo -e "\n"

echo "4. Crear profesión en MongoDB:"
curl -s -X POST 'http://localhost:3000/api/v1/profession/mongo' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "100",
  "name": "Test Engineer Mongo",
  "description": "Profesión de prueba en MongoDB"
}' | jq
echo -e "\n"

echo "5. Obtener profesión ID 100 de MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/maria/100' -H 'accept: application/json' | jq
echo -e "\n"

echo "6. Obtener profesión ID 100 de MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/mongo/100' -H 'accept: application/json' | jq
echo -e "\n"

echo "7. Actualizar profesión ID 100 en MariaDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/profession/maria/100' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "100",
  "name": "Updated Test Engineer Maria",
  "description": "Profesión actualizada en MariaDB"
}' | jq
echo -e "\n"

echo "8. Actualizar profesión ID 100 en MongoDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/profession/mongo/100' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "100",
  "name": "Updated Test Engineer Mongo",
  "description": "Profesión actualizada en MongoDB"
}' | jq
echo -e "\n"

echo "9. Contar profesiones en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/maria/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "10. Contar profesiones en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/profession/mongo/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "11. Eliminar profesión ID 100 de MariaDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/profession/maria/100' -H 'accept: application/json' | jq
echo -e "\n"

echo "12. Eliminar profesión ID 100 de MongoDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/profession/mongo/100' -H 'accept: application/json' | jq
echo -e "\n"

echo "======================================"
echo "Testing Persona Endpoints"
echo "======================================"
echo ""

echo "13. Listar personas en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/maria' -H 'accept: application/json' | jq
echo -e "\n"

echo "14. Listar personas en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/mongo' -H 'accept: application/json' | jq
echo -e "\n"

echo "15. Crear persona en MariaDB:"
curl -s -X POST 'http://localhost:3000/api/v1/persona/maria' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "dni": "999888777",
  "firstName": "Test",
  "lastName": "Maria User",
  "age": "30",
  "sex": "MALE"
}' | jq
echo -e "\n"

echo "16. Crear persona en MongoDB:"
curl -s -X POST 'http://localhost:3000/api/v1/persona/mongo' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "dni": "999888777",
  "firstName": "Test",
  "lastName": "Mongo User",
  "age": "25",
  "sex": "FEMALE"
}' | jq
echo -e "\n"

echo "17. Obtener persona CC 999888777 de MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/maria/999888777' -H 'accept: application/json' | jq
echo -e "\n"

echo "18. Obtener persona CC 999888777 de MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/mongo/999888777' -H 'accept: application/json' | jq
echo -e "\n"

echo "19. Actualizar persona CC 999888777 en MariaDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/persona/maria/999888777' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "dni": "999888777",
  "firstName": "Updated Test",
  "lastName": "Maria User",
  "age": "31",
  "sex": "MALE"
}' | jq
echo -e "\n"

echo "20. Actualizar persona CC 999888777 en MongoDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/persona/mongo/999888777' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "dni": "999888777",
  "firstName": "Updated Test",
  "lastName": "Mongo User",
  "age": "26",
  "sex": "FEMALE"
}' | jq
echo -e "\n"

echo "21. Contar personas en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/maria/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "22. Contar personas en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/persona/mongo/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "23. Eliminar persona CC 999888777 de MariaDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/persona/maria/999888777' -H 'accept: application/json' | jq
echo -e "\n"

echo "24. Eliminar persona CC 999888777 de MongoDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/persona/mongo/999888777' -H 'accept: application/json' | jq
echo -e "\n"

echo "======================================"
echo "Testing Phone Endpoints"
echo "======================================"
echo ""

echo "25. Listar teléfonos en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/maria' -H 'accept: application/json' | jq
echo -e "\n"

echo "26. Listar teléfonos en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/mongo' -H 'accept: application/json' | jq
echo -e "\n"

echo "27. Crear teléfono en MariaDB:"
curl -s -X POST 'http://localhost:3000/api/v1/phone/maria' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "num": "3001112222",
  "oper": "Claro",
  "ownerId": "123456789"
}' | jq
echo -e "\n"

echo "28. Crear teléfono en MongoDB:"
curl -s -X POST 'http://localhost:3000/api/v1/phone/mongo' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "num": "3001112222",
  "oper": "Movistar",
  "ownerId": "123456789"
}' | jq
echo -e "\n"

echo "29. Obtener teléfono 3001112222 de MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/maria/3001112222' -H 'accept: application/json' | jq
echo -e "\n"

echo "30. Obtener teléfono 3001112222 de MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/mongo/3001112222' -H 'accept: application/json' | jq
echo -e "\n"

echo "31. Actualizar teléfono 3001112222 en MariaDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/phone/maria/3001112222' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "num": "3001112222",
  "oper": "Claro Updated",
  "ownerId": "123456789"
}' | jq
echo -e "\n"

echo "32. Actualizar teléfono 3001112222 en MongoDB:"
curl -s -X PUT 'http://localhost:3000/api/v1/phone/mongo/3001112222' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "num": "3001112222",
  "oper": "Movistar Updated",
  "ownerId": "123456789"
}' | jq
echo -e "\n"

echo "33. Contar teléfonos en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/maria/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "34. Contar teléfonos en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/phone/mongo/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "35. Eliminar teléfono 3001112222 de MariaDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/phone/maria/3001112222' -H 'accept: application/json' | jq
echo -e "\n"

echo "36. Eliminar teléfono 3001112222 de MongoDB:"
curl -s -X DELETE 'http://localhost:3000/api/v1/phone/mongo/3001112222' -H 'accept: application/json' | jq
echo -e "\n"

echo "======================================"
echo "Testing Study Endpoints"
echo "======================================"
echo ""

echo "37. Listar estudios en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/maria' -H 'accept: application/json' | jq
echo -e "\n"

echo "38. Listar estudios en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/mongo' -H 'accept: application/json' | jq
echo -e "\n"

echo "39. Crear estudio en MariaDB (Persona 123456789 - Profesión 1):"
curl -s -X POST 'http://localhost:3000/api/v1/estudios/maria' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personId": "123456789",
  "professionId": "1",
  "graduationDate": "15-11-2023",
  "universityName": "Universidad Javeriana"
}' | jq
echo -e "\n"

echo "40. Crear estudio en MongoDB (Persona 123456789 - Profesión 1):"
curl -s -X POST 'http://localhost:3000/api/v1/estudios/mongo' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personId": "123456789",
  "professionId": "1",
  "graduationDate": "15-11-2023",
  "universityName": "Universidad Nacional"
}' | jq
echo -e "\n"

echo "41. Obtener estudio de MariaDB (Persona 123456789 - Profesión 1):"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/maria/123456789/1' -H 'accept: application/json' | jq
echo -e "\n"

echo "42. Obtener estudio de MongoDB (Persona 123456789 - Profesión 1):"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/mongo/123456789/1' -H 'accept: application/json' | jq
echo -e "\n"

echo "43. Actualizar estudio en MariaDB (Persona 123456789 - Profesión 1):"
curl -s -X PUT 'http://localhost:3000/api/v1/estudios/maria/123456789/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personId": "123456789",
  "professionId": "1",
  "graduationDate": "20-12-2023",
  "universityName": "Universidad Javeriana - Actualizada"
}' | jq
echo -e "\n"

echo "44. Actualizar estudio en MongoDB (Persona 123456789 - Profesión 1):"
curl -s -X PUT 'http://localhost:3000/api/v1/estudios/mongo/123456789/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "personId": "123456789",
  "professionId": "1",
  "graduationDate": "20-12-2023",
  "universityName": "Universidad Nacional - Actualizada"
}' | jq
echo -e "\n"

echo "45. Contar estudios en MariaDB:"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/maria/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "46. Contar estudios en MongoDB:"
curl -s -X GET 'http://localhost:3000/api/v1/estudios/mongo/count' -H 'accept: application/json' | jq
echo -e "\n"

echo "47. Eliminar estudio de MariaDB (Persona 123456789 - Profesión 1):"
curl -s -X DELETE 'http://localhost:3000/api/v1/estudios/maria/123456789/1' -H 'accept: application/json' | jq
echo -e "\n"

echo "48. Eliminar estudio de MongoDB (Persona 123456789 - Profesión 1):"
curl -s -X DELETE 'http://localhost:3000/api/v1/estudios/mongo/123456789/1' -H 'accept: application/json' | jq
echo -e "\n"

echo "======================================"
echo "All Tests Completed!"
echo "======================================"