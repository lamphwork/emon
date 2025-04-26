rootProject.name = "emon"

include("core")
include("core:auth")
include("core:common")

include("services")
include("services:account-svc")
include("shared")
include("shared:grpc")
findProject(":shared:grpc")?.name = "grpc"
