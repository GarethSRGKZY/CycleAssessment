# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Result IDs
## Static Methods
- `public findResultById(ArrayList<Result> resultInstances, int stageId, int riderId): Result`

- `public loadResults(ArrayList<Result> resultInstances): void`
	- Deserialise Results onto memory
- `public saveResults(ArrayList<Result> resultInstances): void`
	- Serialise Results onto disk

- `public toIds(ArrayList<Result> resultInstances): int[]`
- `public toString(ArrayList<Result> resultInstances): String`
# Instance
## Instance Attributes
- `private id: int`
- `private stage: Stage`
	- Reference to aggregated Stage
- `private rider: Rider`
	- Reference to aggregated Rider
- `private checkpointTimes: LocalTime[]`
	- Same length as `this.stage.getCheckpoints()`
## Instance Methods
- `public Result(Stage stage, Rider rider, LocalTime... checkpoints)`
	- Constructor

- `public toString(): String`
- `public getId(): int`