# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Race IDs
- `private raceInstances: ArrayList<Race>`
	- Storage of all Race instances
## Static Methods
- `public getRaceIds(): int[]`

- `public getRaceById(int id): Race`
- `public removeRaceById(int id): void`

- `public loadRaces(): void`
	- Deserialise Races into memory
# Instance
## Instance Attributes
- `private id: int`
- `private stages: ArrayList<Integer>`
	- IDs of all aggregated Stages

- `private name: String`
- `private description: String`
## Instance Methods
- `public Race(String name, String description)`
	- Constructor

- `public getStages(): int[]`
	- Getter for `stages` Instance Attribute
	- Returns Stage IDs
- `public addStage(int id): void`
	- Append to `stages` Instance Attribute
- `public removeStage(int id): void`
	- Remove from `stages` Instance Attribute

- `public toString(): String`
- `public getId(): int`