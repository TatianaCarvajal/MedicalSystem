# MedicalSystem
This project consists of the creation of an API Rest that allows you to create an appointment with a doctor and patient without date and time duplication.

## Architecture
This App uses clean architecture.

## Technologies
- JAVA
- SpringWeb
- SpringData – JPA
- Lombok
- MySQL
- JUnit
- Mockito
- Maven
- ModelMapper

## Use in postman
### Doctor
Create and update methods need a request body.

#### Create
```
{
    "name": "Juan",
    "specialty": "UROLOGY",
    "phone": "356862965"
}
```
#### Update
```
{
    "id": 10,
    "name": "Felipe",
    "specialty": "CARDIOLOGY",
    "phone": "123456789"
}
```

### Patient
Create and update methods need a request body.

#### Create
```
{
    "name": "Luis",
    "document": "12345",
    "phone": "356862965"
}
```
#### Update
```
{
    "id": 1,
    "name": "Josh",
    "document": "12345",
    "phone": "123456789"
}
```
### Appointment
Create and update methods need a request body.
#### Create
```
{
    "doctor_id": 2,
    "patient_id": 1,
    "dateTime": "2025-10-25T10:30:00"
}
```
#### Update
```
{
    "id": 14,
    "doctor_id": 7,
    "patient_id": 5,
    "dateTime": "2025-09-25T10:30:00"
}
```
## Swagger documentation

| Doctor |
| --- |
| <img width="1343" height="367" alt="Doctor" src="https://github.com/user-attachments/assets/65c1899e-e837-40c8-b1c6-6c48c60807be" /> | 

| Patient |
| --- |
| <img width="1346" height="289" alt="Patient" src="https://github.com/user-attachments/assets/e4aab397-f32e-4aa7-b280-e9373af2b8ae" /> |

| Appointment |
| --- |
| <img width="1343" height="391" alt="Appointment" src="https://github.com/user-attachments/assets/90e6cdb2-8967-45e1-98ff-6cc199a1b155" /> |
