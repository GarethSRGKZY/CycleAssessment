# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Race IDs
## Static Methods
- `public getRaceIds(ArrayList<Race> raceInstances): int[]`

- `public getRaceById(ArrayList<Race> raceInstances, int id): Race`
- `public removeRaceById(ArrayList<Race> raceInstances, int id): void`
- `public createRace(ArrayList<Race> raceInstances, String name, String description): Race`
	- Wrapper for the Race constructor

- `public loadRaces(ArrayList<Race> raceInstances): void`
	- Deserialise Races onto memory
- `public saveRaces(ArrayList<Race> raceInstances: void`
	- Serialise Races onto disk
# Instance
## Instance Attributes
- `private id: int`
- `private stages: ArrayList<Stage>`
	- References to all aggregated Stages

- `private name: String`
- `private description: String`
## Instance Methods
- `private Race(String name, String description)`
	- Constructor

- `public getStages(): Stage[]`
	- Getter for `stages` Instance Attribute
	- Returns Stage references
- `public addStage(Stage stage): void`
	- Append to `stages` Instance Attribute
- `public removeStage(Stage stage): void`
	- Remove from `stages` Instance Attribute

- `public toString(): String`
- `public getId(): int`