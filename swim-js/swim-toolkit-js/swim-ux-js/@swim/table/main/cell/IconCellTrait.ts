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

import type {Class} from "@swim/util";
import {Property} from "@swim/fastener";
import type {Graphics} from "@swim/graphics";
import {CellTrait} from "./CellTrait";
import type {IconCellTraitObserver} from "./IconCellTraitObserver";

export class IconCellTrait extends CellTrait {
  override readonly observerType?: Class<IconCellTraitObserver>;

  protected willSetIcon(newIcon: Graphics | null, oldIcon: Graphics | null): void {
    const observers = this.observers;
    for (let i = 0, n = observers.length; i < n; i += 1) {
      const traitObserver = observers[i]!;
      if (traitObserver.traitWillSetIcon !== void 0) {
        traitObserver.traitWillSetIcon(newIcon, oldIcon, this);
      }
    }
  }

  protected onSetIcon(newIcon: Graphics | null, oldIcon: Graphics | null): void {
    // hook
  }

  protected didSetIcon(newIcon: Graphics | null, oldIcon: Graphics | null): void {
    const observers = this.observers;
    for (let i = 0, n = observers.length; i < n; i += 1) {
      const traitObserver = observers[i]!;
      if (traitObserver.traitDidSetIcon !== void 0) {
        traitObserver.traitDidSetIcon(newIcon, oldIcon, this);
      }
    }
  }

  @Property<IconCellTrait, Graphics | null>({
    state: null,
    willSetState(newIcon: Graphics | null, oldIcon: Graphics | null): void {
      this.owner.willSetIcon(newIcon, oldIcon);
    },
    didSetState(newIcon: Graphics | null, oldIcon: Graphics | null): void {
      this.owner.onSetIcon(newIcon, oldIcon);
      this.owner.didSetIcon(newIcon, oldIcon);
    },
  })
  readonly icon!: Property<this, Graphics | null>;
}
