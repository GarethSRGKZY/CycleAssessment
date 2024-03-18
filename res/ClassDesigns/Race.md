# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Race IDs
## Static Methods
- `public findRaceById(ArrayList<Race> raceInstances, int raceId): Race`
- `public findRaceByName(ArrayList<Race> raceInstances, String raceName): Race`
- `public findRaceContainsStage(ArrayList<Race> raceInstances, int stageId): Race`

- `public eraseRaces(ArrayList<Race> raceInstances): void`
- `public loadRaces(ArrayList<Race> raceInstances): void`
	- Deserialise Races onto memory
- `public saveRaces(ArrayList<Race> raceInstances: void`
	- Serialise Races onto disk

- `public toIds(ArrayList<Race> raceInstances): int[]`
- `public toString(ArrayList<Race> raceInstances): String`
	- Join toStrings of each Race
# Instance
## Instance Attributes
- `private id: int`
- `private stages: ArrayList<Stage>`
	- References to all aggregated Stages

- `private name: String`
- `private description: String`
## Instance Methods
- `public Race(String name, String description)`
	- Constructor

- `public getName(): String`
	- Getter for `name` Instance Attribute

- `public getStages(): ArrayList<Stage>`
	- Getter for `stages` Instance Attribute
	- Returns Stage references
- `public addStage(Stage stage): void`
	- Append to `stages` Instance Attribute
- `public removeStage(Stage stage): void`
	- Remove from `stages` Instance Attribute

- `public toString(): String`
- `public getId(): int`