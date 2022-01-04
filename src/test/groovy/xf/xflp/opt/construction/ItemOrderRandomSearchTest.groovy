package xf.xflp.opt.construction

import spock.lang.Specification
import xf.xflp.XFLP
import xf.xflp.opt.XFLPOptType

class ItemOrderRandomSearchTest extends Specification {

    def "test random search"() {
        XFLP xflp = build()
        xflp.setTypeOfOptimization(XFLPOptType.FAST_FIXED_CONTAINER_PACKER)

        XFLP xflp2 = build()
        xflp2.setTypeOfOptimization(XFLPOptType.BEST_FIXED_CONTAINER_PACKER)

        when:
        xflp.executeLoadPlanning()
        def rep = xflp.getReport()

        xflp2.executeLoadPlanning()
        def rep2 = xflp2.getReport()

        then:
        rep.getUnplannedPackages().size() > 0
        rep2.getUnplannedPackages().size() == 0
    }

    private XFLP build() {
        def xflp = new XFLP()
        xflp.addContainer().setContainerType("C1").setWidth(3).setLength(3).setHeight(3)
        xflp.addItem().setExternID("P").setWidth(1).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P").setWidth(1).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P").setWidth(1).setLength(1).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P").setWidth(2).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P").setWidth(2).setLength(1).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P").setWidth(2).setLength(2).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P").setWidth(2).setLength(1).setHeight(2).setWeight(1)
        xflp.addItem().setExternID("P").setWidth(3).setLength(1).setHeight(2).setWeight(1)

        xflp.addItem().setExternID("P").setWidth(3).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P").setWidth(3).setLength(1).setHeight(1).setWeight(1)
        xflp
    }
}
