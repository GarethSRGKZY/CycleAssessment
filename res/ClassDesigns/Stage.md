# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Stage IDs
- `private stageInstances: ArrayList<Stage>`
	- Storage of all Stage instances
## Static Methods
- `public getStageIds(): int[]`

- `public getStageById(int id): Stage`
- `public removeStageById(int id): void`

- `public loadStages(): void`
	- Deserialise Stages onto memory
- `public saveStages(): void`
	- Serialise Stages onto disk
# Instance
## Instance Attributes
- `private id: int`
- `private checkpoints: ArrayList<Integer>`
	- IDs of all aggregated Checkpoints

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
- `public Stage(String name, String description, double length, LocalDateTime startTime, StageType type)`
	- Constructor

- `public getLength(): double`
	- Getter for `length` Instance Attribute

- `public getCheckpoints(): int[]`
	- Getter for `checkpoints` Instance Attribute
	- Returns Checkpoint IDs
- `public addCheckpoint(int id): void`
	- Append to `checkpoints` Instance Attribute
- `public removeCheckpoint(int id): void`
	- Delete from `checkpoints` Instance Attribute

- `public toString(): String`
- `public getId(): int`