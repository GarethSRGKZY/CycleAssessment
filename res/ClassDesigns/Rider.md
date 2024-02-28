# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Rider IDs
## Static Methods
- `public getRiderIds(ArrayList<Rider> riderInstances): int[]`

- `public getRiderById(ArrayList<Rider> riderInstances, int id): Rider`
- `public removeRiderById(ArrayList<Rider> riderInstances, int id): void`
- `public createRider(ArrayList<Rider> riderInstances, String name, int yearOfBirth)`
	- Wrapper for the Rider constructor

- `public loadRiders(ArrayList<Rider> riderInstances): void`
	- Deserialise Riders onto memory
- `public saveRiders(ArrayList<Rider> riderInstances): void`
	- Serialise Riders onto disk
# Instance
## Instance Attributes
- `private id: int`

- `private name: String`
- `private yearOfBirth: int`
## Instance Methods
- `private Rider(String name, int yearOfBirth)`
	- Constructor

- `public toString(): String`
- `public getId(): int`