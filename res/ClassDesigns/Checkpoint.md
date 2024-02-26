# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Checkpoint IDs
- `private checkpointInstances: ArrayList<Stage>`
	- Storage of all Checkpoint instances
## Static Methods
- `public getCheckpointIds(): int[]`

- `public getCheckpointById(int id): Checkpoint`
- `public removeCheckpointById(int id): void`

- `public loadCheckpoints(): void`
	- Deserialise Checkpoints onto memory
- `public saveCheckpoints(): void`
	- Serialise Checkpoints onto disk
# Instance
## Instance Attributes
- `private id: int`

- `private location: double`
- `private averageGradient: double`
- `private length: double`
- `private type: CheckpointType`
	- Enum type
		- SPRINT
		- C4
		- C3
		- C2
		- C1
		- HC
## Instance Methods
- `public Checkpoint(double location, CheckpointType type, double averageGradient, double length)`
	- Constructor for *Categorised Climb*
- `public Checkpoint(double location)`
	- Overloaded constructor for *Intermediate Sprint*

- `public toString(): String`
- `public getId(): int`