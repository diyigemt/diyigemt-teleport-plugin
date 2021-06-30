# diyigemt-teleport-plugin
a nukkit plugin allows you use classic teleport command in nukkit

# intsall
1.download jar from [release](https://github.com/diyigemt/diyigemt-teleport-plugin/releases/tag/v1.0.0)

2.put it into plugins folder

3.start server and enjoy it



**nukkit api version: 1.0.9**

# commands

| namd      | args        | default | description                                             |
| --------- | ----------- | ------- | ------------------------------------------------------- |
| /sethome  | home name   | home    | set a home                                              |
| /home     | home name   | home    | return a home                                           |
| /tpa      | player name | null    | request teleport to a plyer                             |
| /tpa      | ac / rj     | null    | accept or reject request                                |
| /setspawn | null        | null    | set this point as respwan point of the level you are at |
| /setspawn | main        | null    | set this point as main respwan point of all level       |
| /spawn    | null        | null    | return the spawn point in this level                    |
| /spawn    | main        | null    | return the main spawn point                             |
| /back     | null        | null    | return the last teleport point                          |

# additional
command **/spawn** will teleport player to return the spawn point in this level, if the respown point of this level is **not set yet**, player will be teleport to the main respawn point
