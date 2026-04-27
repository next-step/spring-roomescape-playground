## Reservation System

--- 
### GET

```
   GET /reservations HTTP/1.1
```
- Response
  ```
    HTTP/1.1 200 
    Content-Type: application/json
    
    [
    {
    "id": 1,
    "name": "브라운",
    "date": "2023-01-01",
    "time": "10:00"
    },
    {
    "id": 2,
    "name": "브라운",
    "date": "2023-01-02",
    "time": "11:00"
    }
    ]
  ``` 
  
---
### POST
```
   POST /reservations HTTP/1.1
    // 데이터
    content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```
- Response
   ```
    HTTP/1.1 201 
    Location: /reservations/1
    Content-Type: application/json
    
    {
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
    }
   ```
          
--- 
### DELETE
```
DELETE /reservations/1 HTTP/1.1
```
- Response
  ```
  HTTP/1.1 204 No Content
  ```
