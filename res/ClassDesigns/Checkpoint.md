# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Checkpoint IDs
## Static Methods
- `public getCheckpointIds(ArrayList<Checkpoint> checkpointInstances): int[]`

- `public getCheckpointById(ArrayList<Checkpoint> checkpointInstances, int id): Checkpoint`
- `public removeCheckpointById(ArrayList<Checkpoint> checkpointInstances, int id): void`
- `public createCheckpoint(ArrayList<Checkpoint> checkpointInstances, double location, CheckpointType type, double averageGradient, double length)`
	- Wrapper for the Checkpoint constructor
- `public createCheckpoint(ArrayList<Checkpoint> checkpointInstances, double location)`
	- Overloaded wrapper for the Checkpoint constructor

- `public loadCheckpoints(ArrayList<Checkpoint> checkpointInstances): void`
	- Deserialise Checkpoints onto memory
- `public saveCheckpoints(ArrayList<Checkpoint> checkpointInstances): void`
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
- `private Checkpoint(double location, CheckpointType type, double averageGradient, double length)`
	- Constructor for *Categorised Climb*
- `private Checkpoint(double location)`
	- Overloaded constructor for *Intermediate Sprint*

- `public getLocation(): double`
	- Getter for `location` Instance Attribute

- `public toString(): String`
- `public getId(): int`