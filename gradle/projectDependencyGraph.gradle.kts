tasks.register("module-graph-data") {
    doLast {
        exec {
            commandLine("./gradlew", "graphModules", "-PgraphFilter=shared:data,shared:data:local,shared:data:remote")
        }
    }
}

tasks.register("module-graph-domain") {
    doLast {
        exec {
            commandLine("./gradlew", "graphModules", "-PgraphFilter=shared:domain")
        }
    }
}

tasks.register("module-graph-all") {
    doLast {
        exec {
            commandLine("./gradlew", "graphModules")
        }
    }
}