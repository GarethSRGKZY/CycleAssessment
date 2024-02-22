# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Team IDs
- `private teamInstances: ArrayList<Race>`
	- Storage of all Team instances
## Static Methods
- `public getTeamIDs(): int[]`

- `public getTeamById(int id): Checkpoint`
- `public removeTeamById(int id): void`

- `public loadTeams(): void`
	- Deserialise Teams into memory
# Instance
## Instance Attributes
- `private id: int`
- `private riders: ArrayList<Integer>`
	- IDs of all aggregated Riders

- `private name: String`
- `private description: String`
## Instance Methods
- `public Team(String name, String description)`
	- Constructor

- `public getRiders(): int[]`
	- Getter for `riders` Instance Attribute
	- Returns Rider IDs
- `public addRider(int id): void`
	- Append to `riders` Instance Attribute
- `public removeRider(int id): void`
	- Remove from `riders` Instance Attribute

- `public toString(): String`
- `public getId(): int`