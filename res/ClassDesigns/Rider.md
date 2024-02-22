# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Rider IDs
- `private riderInstances: ArrayList<Rider>`
	- Storage of all Rider instances
## Static Methods
- `public getRiderIDs(): int[]`

- `public getRiderById(int id): Checkpoint`
- `public removeRiderById(int id): void`

- `public loadRiders(): void`
	- Deserialise Riders onto memory
- `public saveRiders(): void`
	- Serialise Riders onto disk
# Instance
## Instance Attributes
- `private id: int`

- `private name: String`
- `private yearOfBirth: int`
## Instance Methods
- `public Rider(String name, int yearOfBirth)`
	- Constructor

- `public toString(): String`
- `public getId(): int`