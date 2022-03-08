// Copyright 2015-2022 Swim.inc
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

import {KeyEffect} from "../KeyEffect";
import {AbstractMapInlet} from "../AbstractMapInlet";

/** @public */
export abstract class WatchFieldsOperator<K, V, O> extends AbstractMapInlet<K, V, O> {
  protected override onRecohereOutputKey(key: K, effect: KeyEffect, version: number): void {
    if (effect === KeyEffect.Update) {
      const input = this.input;
      if (input !== null) {
        this.evaluate(key, input.get(key));
      } else {
        this.evaluate(key, void 0);
      }
    } else if (effect === KeyEffect.Remove) {
      this.evaluate(key, void 0);
    }
  }

  abstract evaluate(key: K, value: V | undefined): void;
}
