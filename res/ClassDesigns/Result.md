# Static
## Static Attributes
## Static Methods
- `public findResultById(ArrayList<Result> resultInstances, int stageId, int riderId): Result`

- `public eraseResults(ArrayList<Result> resultInstances): void`

- `public timeDelta(LocalTime time1, LocalTime time2): LocalTime`
- `public timeAdd(LocalTime time1, LocalTime time2): LocalTime`
- `public adjustElapsedTimes(LocalTime[] elapsedTimes): LocalTime[]`

- `public toElapsedTimes(ArrayList<Result> resultInstances): LocalTime[]`
- `public toString(ArrayList<Result> resultInstances): String`
# Instance
## Instance Attributes
- `private stage: Stage`
	- Reference to aggregated Stage
- `private rider: Rider`
	- Reference to aggregated Rider
- `private checkpointTimes: LocalTime[]`
	- Same length as `this.stage.getCheckpoints() + 2`
## Instance Methods
- `public Result(Stage stage, Rider rider, LocalTime... checkpoints)`
	- Constructor
	- Assert `checkpoints >= 2` (start & finish time)

- `public getStage(): Stage`
- `public getRider(): Rider`

- `public toString(): String`