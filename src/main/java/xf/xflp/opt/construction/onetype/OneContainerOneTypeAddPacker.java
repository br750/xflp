package xf.xflp.opt.construction.onetype;

import xf.xflp.base.XFLPModel;
import xf.xflp.base.container.Container;
import xf.xflp.base.item.Item;
import xf.xflp.exception.XFLPException;
import xf.xflp.opt.Packer;
import xf.xflp.opt.construction.strategy.Strategy;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright (c) 2012-2022 Holger Schneider
 * All rights reserved.
 *
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 *
 * This packer puts the items in a sequence into one container with one container type.
 * Items will only be added to a container.
 *
 * @author hschneid
 *
 */
public class OneContainerOneTypeAddPacker implements Packer {

	private boolean isInit = false;
	private ZSingleBinAddPacker packer;

	@Override
	public void execute(XFLPModel model) throws XFLPException {
		init(model);

		Container container = model.getContainerTypes()[0].newInstance();

		List<Item> unplannedItemList = packer.createLoadingPlan(
				Arrays.asList(model.getItems()),
				container
		);

		// Put result into model
		model.setContainers(new Container[]{container});
		model.setUnplannedItems(unplannedItemList.toArray(new Item[0]));
	}

	private void init(XFLPModel model) {
		if(!isInit) {
			isInit = true;
			Strategy strategy = model.getParameter().getPreferredPackingStrategy();
			packer = new ZSingleBinAddPacker(strategy);
		}
	}

}
