rootProject.name = "uslerung"

include(":common")

include(":app")

include(":app:presentation")

include(":app:application")
include(":app:application:user-application")
include(":app:application:schedule-application")

include(":app:domain")
include(":app:domain:user-domain")
include(":app:domain:schedule-domain")

include(":app:external")
include(":app:external:slack")
