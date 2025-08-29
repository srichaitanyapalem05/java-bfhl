# BFHL REST API (Java / Spring Boot)

Implements the /bfhl POST endpoint per the VIT question. Returns:
- is_success
- user_id (full_name_ddmmyyyy, lowercase, underscores)
- email
- roll_number
- odd_numbers (as strings)
- even_numbers (as strings)
- alphabets (uppercased tokens)
- special_characters
- sum (string)
- concat_string (reverse of all alphabetical characters, alternating caps)

## Build & Run
```bash
mvn -q -DskipTests package
java -jar target/bfhl-0.0.1-SNAPSHOT.jar
```

## Deploy (Render/Railway)
- **Start command**: `java -jar target/bfhl-0.0.1-SNAPSHOT.jar`
- Exposes port from env `PORT` (fallback 8080).
- Path: `POST /bfhl`

## Example Request
```json
{ "data": ["a","1","334","4","R","$"] }
```

## Optional fields in request
You may send `full_name`, `dob_ddmmyyyy`, `email`, and `roll_number` to auto-fill response identity fields.
