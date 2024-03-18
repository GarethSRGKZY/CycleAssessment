# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Stage IDs
## Static Methods
- `public findStageById(ArrayList<Stage> stageInstances, int stageId): Stage`
- `public findStageByName(ArrayList<Stage> stageInstances, String stageName): Stage`
- `public findStageContainsCheckpoint(ArrayList<Stage>, int checkpointId)`

- `public eraseStages(): void`
- `public loadStages(ArrayList<Stage> stageInstances): void`
	- Deserialise Stages onto memory

- `public toIds(ArrayList<Stage> stageInstances): int[]`
- `public toString(ArrayList<Stage> stageInstances): String`
	- Join toStrings of each Stage
# Instance
## Instance Attributes
- `private id: int`
- `private checkpoints: ArrayList<Checkpoint>`
	- References to all aggregated Checkpoints

- `private name: String`
- `private description: String`
- `private length: double`
- `private startTime: LocalDateTime`
- `private type: StageType`
	- Enum type
		- FLAT
		- MEDIUM_MOUNTAIN
		- HIGH_MOUNTAIN
		- TT
- `private state: StageState`
	- Enum type
		- PREPARING_STAGE
		- WAITING_FOR_RESULTS
## Instance Methods
- `public Stage(String name, String description, double length, LocalDateTime startTime, StageType type)`
	- Constructor

- `public getName(): String`
	- Getter for `name` Instance Attribute
- `public getLength(): double`
	- Getter for `length` Instance Attribute
- `public getStartTime(): LocalDateTime`
	- Getter for `startTime` Instance Attribute

- `public getCheckpoints(): ArrayList<Checkpoint>`
	- Getter for `checkpoints` Instance Attribute
	- Returns Checkpoint references
- `public addCheckpoint(Checkpoint checkpoint): void`
	- Append to `checkpoints` Instance Attribute
- `public removeCheckpoint(Checkpoint checkpoint): void`
	- Delete from `checkpoints` Instance Attribute

- `public concludePreparation(): void`
	- Change the `state` Instance Attribute to `StageState.WAITING_FOR_RESULTS`

- `public toString(): String`
- `public getId(): int`