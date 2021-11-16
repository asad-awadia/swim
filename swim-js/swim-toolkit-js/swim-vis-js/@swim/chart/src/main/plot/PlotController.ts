// Copyright 2015-2021 Swim Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import {Class, AnyTiming, Timing} from "@swim/util";
import {Property} from "@swim/fastener";
import type {TraitViewRef} from "@swim/controller";
import {DataSetController} from "../data/DataSetController";
import type {PlotView} from "./PlotView";
import type {PlotTrait} from "./PlotTrait";
import {BubblePlotTrait} from "./BubblePlotTrait";
import {LinePlotTrait} from "./LinePlotTrait";
import {AreaPlotTrait} from "./AreaPlotTrait";
import type {PlotControllerObserver} from "./PlotControllerObserver";
import {BubblePlotController} from "../"; // forward import
import {LinePlotController} from "../"; // forward import
import {AreaPlotController} from "../"; // forward import

/** @public */
export abstract class PlotController<X = unknown, Y = unknown> extends DataSetController<X, Y> {
  override readonly observerType?: Class<PlotControllerObserver<X, Y>>;

  @Property({type: Timing, inherits: true})
  readonly plotTiming!: Property<this, Timing | boolean | undefined, AnyTiming>;

  abstract readonly plot: TraitViewRef<this, PlotTrait<X, Y>, PlotView<X, Y>>;

  static fromTrait<X, Y>(plotTrait: PlotTrait<X, Y>): PlotController<X, Y> {
    if (plotTrait instanceof BubblePlotTrait) {
      return new BubblePlotController<X, Y>();
    } else if (plotTrait instanceof LinePlotTrait) {
      return new LinePlotController<X, Y>();
    } else if (plotTrait instanceof AreaPlotTrait) {
      return new AreaPlotController<X, Y>();
    } else {
      throw new Error("Can't create PlotController from " + plotTrait);
    }
  }
}