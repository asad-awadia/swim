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

import {TraitProperty} from "@swim/model";
import type {HtmlView} from "@swim/dom";
import {CellTrait} from "./CellTrait";
import type {TextCellTraitObserver} from "./TextCellTraitObserver";

export type TextCellContent = TextCellContentFunction | string;
export type TextCellContentFunction = (cellTrait: TextCellTrait) => HtmlView | string | null;

export class TextCellTrait extends CellTrait {
  override readonly traitObservers!: ReadonlyArray<TextCellTraitObserver>;

  protected willSetContent(newContent: TextCellContent | null, oldContent: TextCellContent | null): void {
    const traitObservers = this.traitObservers;
    for (let i = 0, n = traitObservers.length; i < n; i += 1) {
      const traitObserver = traitObservers[i]!;
      if (traitObserver.traitWillSetContent !== void 0) {
        traitObserver.traitWillSetContent(newContent, oldContent, this);
      }
    }
  }

  protected onSetContent(newContent: TextCellContent | null, oldContent: TextCellContent | null): void {
    // hook
  }

  protected didSetContent(newContent: TextCellContent | null, oldContent: TextCellContent | null): void {
    const traitObservers = this.traitObservers;
    for (let i = 0, n = traitObservers.length; i < n; i += 1) {
      const traitObserver = traitObservers[i]!;
      if (traitObserver.traitDidSetContent !== void 0) {
        traitObserver.traitDidSetContent(newContent, oldContent, this);
      }
    }
  }

  @TraitProperty<TextCellTrait, TextCellContent | null>({
    state: null,
    willSetState(newContent: TextCellContent | null, oldContent: TextCellContent | null): void {
      this.owner.willSetContent(newContent, oldContent);
    },
    didSetState(newContent: TextCellContent | null, oldContent: TextCellContent | null): void {
      this.owner.onSetContent(newContent, oldContent);
      this.owner.didSetContent(newContent, oldContent);
    },
  })
  readonly content!: TraitProperty<this, TextCellContent | null>;
}