# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Checkpoint IDs
## Static Methods
- `public findCheckpointById(ArrayList<Checkpoint> checkpointInstances, int checkpointId): Checkpoint`

- `public loadCheckpoints(ArrayList<Checkpoint> checkpointInstances): void`
	- Deserialise Checkpoints onto memory
- `public saveCheckpoints(ArrayList<Checkpoint> checkpointInstances): void`
	- Serialise Checkpoints onto disk

- `public toIds(ArrayList<Checkpoint> checkpointInstances): int[]`
- `public toString(ArrayList<Checkpoint> checkpointInstances): String`
	- Join toStrings of each Checkpoint
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
- `private Checkpoint(double location)`
	- Overloaded constructor for *Intermediate Sprint*

- `public getLocation(): double`
	- Getter for `location` Instance Attribute

- `public toString(): String`
- `public getId(): int`