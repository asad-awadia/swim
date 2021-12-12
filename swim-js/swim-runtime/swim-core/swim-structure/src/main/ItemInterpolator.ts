// Copyright 2015-2021 Swim.inc
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

import {Mutable, Interpolator} from "@swim/util";
import type {Item} from "./Item";

/** @internal */
export const ItemInterpolator = (function (_super: typeof Interpolator) {
  const ItemInterpolator = function <Y extends Item>(y0: Y, y1: Y): Interpolator<Y> {
    const interpolator = function (u: number): Y {
      return u < 1 ? interpolator[0] : interpolator[1];
    } as Interpolator<Y>;
    Object.setPrototypeOf(interpolator, ItemInterpolator.prototype);
    (interpolator as Mutable<typeof interpolator>)[0] = y0.commit();
    (interpolator as Mutable<typeof interpolator>)[1] = y1.commit();
    return interpolator;
  } as {
    <Y extends Item>(y0: Y, y1: Y): Interpolator<Y>;

    /** @internal */
    prototype: Interpolator<any>;
  };

  ItemInterpolator.prototype = Object.create(_super.prototype);
  ItemInterpolator.prototype.constructor = ItemInterpolator;

  return ItemInterpolator;
})(Interpolator);