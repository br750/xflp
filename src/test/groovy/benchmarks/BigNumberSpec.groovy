package benchmarks


import spock.lang.Specification
import xf.xflp.XFLP
import xf.xflp.opt.XFLPOptType

import java.util.stream.Collectors

class BigNumberSpec extends Specification {

    XFLP service
    def random = new Random(1234)

    def "1000 boxes"() {
        when:
        long time = System.currentTimeMillis()
        for (i in 0..< 100) {
            fillService()
            service.executeLoadPlanning()
            def result = service.getReport()
            println i + " : " + result.getSummary().getUtilization()
        }
        println "Runtime: " + (System.currentTimeMillis() - time) + " ms"
        then:

        // Vol of boxes <16000
        // Vol of container 15000
        assert true
    }

    private void fillService() {
        service = new XFLP()

        service.addContainer().setWidth(410).setLength(200).setHeight(70).setMaxWeight(10000)
        service.setTypeOfOptimization(XFLPOptType.FAST_FIXED_CONTAINER_PACKER)

        for (int i = 0; i < 10000; i++) {
            int w = randInt(1, 40)
            int l = randInt(1, 20)
            int h = randInt(1, 20)
            int immersiveDepth = getImmersiveDepth(h)
            int weight = randInt(10, 100)
            int bearingWeight = randInt(30, 300)
            int nbrOfItemsToStacked = randInt(1, 100)
            int stackingGroup = randInt(1, 11)
            String stackingGroups = getStackingGroups()
            createItem(i, l, w, h, weight, bearingWeight, nbrOfItemsToStacked, stackingGroup, stackingGroups, immersiveDepth)
        }
    }

    private int getImmersiveDepth(int h) {
        int maxDepth = (int) (h * 0.2)
        int immersiveDepth = 0
        if (maxDepth > 0) {
            immersiveDepth = randInt(0, maxDepth)
        }
        immersiveDepth
    }

    private createItem(int bi,
                       int length,
                       int w,
                       int h,
                       int weight,
                       int bearingWeight,
                       int nbrOfItemsToStacked,
                       int stackingGroup,
                       String stackingGroups,
                       int immersiveDepth
    ) {
        service
                .addItem()
                .setExternID(bi+"")
                .setWidth(w)
                .setHeight(h)
                .setLength(length)
                .setWeight(weight)
                .setStackingWeightLimit(bearingWeight)
                .setNbrOfAllowedStackedItems(nbrOfItemsToStacked)
                .setStackingGroup(stackingGroup+" ")
                .setAllowedStackingGroups(stackingGroups)
                .setImmersiveDepth(immersiveDepth)
    }

    private randInt(int lower, int upper) {
        random.nextInt(upper - lower) + lower
    }

    String getStackingGroups() {
        Set<String> groups = new HashSet<>();
        for (i in 0..<4) {
            groups.add(randInt(1, 11) + "")
        }

        groups.stream().collect(Collectors.joining(","))
    }
}
