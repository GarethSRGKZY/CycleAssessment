# Static
## Static Attributes
- `private nextId: int`
	- Running counter of Team IDs
## Static Methods
- `public findTeamById(ArrayList<Team> teamInstances, int teamId): Team`

- `public eraseTeams(ArrayList<Team> teamInstances): void`
- `public loadTeams(ArrayList<Team> teamInstances): void`
	- Deserialise Teams onto memory

- `public toIds(ArrayList<Team> teamInstances): int[]`
- `public toString(ArrayList<Team> teamInstances): String`
# Instance
## Instance Attributes
- `private id: int`
- `private riders: ArrayList<Rider>`
	- References to all aggregated Riders

- `private name: String`
- `private description: String`
## Instance Methods
- `public Team(String name, String description)`
	- Constructor

- `public getName(): String`
	- Getter for `name` Instance Attribute

- `public getRiders(): ArrayList<Rider>`
	- Getter for `riders` Instance Attribute
	- Returns Rider references
- `public addRider(Rider rider): void`
	- Append to `riders` Instance Attribute
- `public removeRider(Rider rider): void`
	- Remove from `riders` Instance Attribute

- `public toString(): String`
- `public getId(): int`