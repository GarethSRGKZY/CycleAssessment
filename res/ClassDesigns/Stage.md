# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Stage IDs
## Static Methods
- `public getStageIds(ArrayList<Stage> stageInstances): int[]`

- `public getStageById(ArrayList<Stage> stageInstances, int id): Stage`
- `public removeStageById(ArrayList<Stage> stageInstances, int id): void`
- `public createStage(ArrayList<Stage> stageInstances, String name, String description, double length, LocalDateTime startTime, StageType type): Stage`
	- Wrapper for the Stage constructor

- `public loadStages(ArrayList<Stage> stageInstances): void`
	- Deserialise Stages onto memory
- `public saveStages(ArrayList<Stage> stageInstances): void`
	- Serialise Stages onto disk
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
## Instance Methods
- `private Stage(String name, String description, double length, LocalDateTime startTime, StageType type)`
	- Constructor

- `public getLength(): double`
	- Getter for `length` Instance Attribute

- `public getCheckpoints(): Checkpoint[]`
	- Getter for `checkpoints` Instance Attribute
	- Returns Checkpoint references
- `public addCheckpoint(Checkpoint checkpoint): void`
	- Append to `checkpoints` Instance Attribute
- `public removeCheckpoint(Checkpoint checkpoint): void`
	- Delete from `checkpoints` Instance Attribute

- `public toString(): String`
- `public getId(): int`