# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Team IDs
## Static Methods
- `public getTeamIds(ArrayList<Team> teamInstances): int[]`

- `public getTeamById(ArrayList<Team> teamInstances, int id): Team`
- `public removeTeamById(ArrayList<Team> teamInstances, int id): void`
- `public createTeam(ArrayList<Team> teamInstances, String name, String description): Team`
	- Wrapper for the Team constructor

- `public loadTeams(ArrayList<Team> teamInstances): void`
	- Deserialise Teams onto memory
- `public saveTeams(ArrayList<Team> teamInstances): void`
	- Serialise Teams onto disk
# Instance
## Instance Attributes
- `private id: int`
- `private riders: ArrayList<Rider>`
	- References to all aggregated Riders

- `private name: String`
- `private description: String`
## Instance Methods
- `private Team(String name, String description)`
	- Constructor

- `public getRiders(): Rider[]`
	- Getter for `riders` Instance Attribute
	- Returns Rider references
- `public addRider(Rider rider): void`
	- Append to `riders` Instance Attribute
- `public removeRider(Rider rider): void`
	- Remove from `riders` Instance Attribute

- `public toString(): String`
- `public getId(): int`