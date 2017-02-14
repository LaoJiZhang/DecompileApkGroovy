class Work {

    def work() {
        println "get work done"
    }

    def analyze() {
        println "Work analyze"
    }

    def writeReport() {
        println "get report written"
    }
}


class Expert {
    def analyze() {
        println("Expert analyze")
    }
}

class Manager {
    @Delegate
    Work work = new Work()
    @Delegate
    Expert expert = new Expert()
}

def bernie = new Manager()
bernie.analyze()
bernie.work()
bernie.writeReport()

