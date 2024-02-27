# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Result IDs
- `private resultInstances: ArrayList<Race>`
	- Storage of all Result instances
## Static Methods
- `public getResultIds(): int[]`

- `public getResultById(int stageId, int riderId): Result`
- `public removeResultById(int stageId, int riderId): void`

- `public loadResults(): void`
	- Deserialise Results onto memory
- `public saveResults(): void`
	- Serialise Results onto disk
# Instance
## Instance Attributes
- `private id: int`
- `private stage: int`
	- ID of aggregated Stage
- `private rider: int`
	- ID of aggregated Rider
- `private checkpointTimes: LocalTime[]`
	- Same length as `this.stage.getCheckpoints()`
## Instance Methods
- `public Result(Stage stage, Rider rider, LocalTime... checkpoints)`
	- Constructor

- `public toString(): String`
- `public getId(): int`