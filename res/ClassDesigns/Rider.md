# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Rider IDs
## Static Methods
- `public findRiderById(ArrayList<Rider> riderInstances, int riderId): Rider`

- `public loadRiders(ArrayList<Rider> riderInstances): void`
	- Deserialise Riders onto memory
- `public saveRiders(ArrayList<Rider> riderInstances): void`
	- Serialise Riders onto disk

- `public toIds(ArrayList<Rider> riderInstances): int[]`
- `public toString(ArrayList<Rider> riderInstances): String`
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