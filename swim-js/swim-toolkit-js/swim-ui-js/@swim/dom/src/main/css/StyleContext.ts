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

/** @public */
export interface StyleContext {
  readonly node?: Node;

  getStyle(propertyNames: string | ReadonlyArray<string>): CSSStyleValue | string | undefined;

  setStyle(propertyName: string, value: unknown, priority?: string): this;
}

/** @public */
export const StyleContext = (function () {
  const StyleContext = {} as {
    is(object: unknown): object is StyleContext;
  };

  StyleContext.is = function (object: unknown): object is StyleContext {
    if (typeof object === "object" && object !== null || typeof object === "function") {
      const styleContext = object as StyleContext;
      return "getStyle" in styleContext;
    }
    return false;
  };

  return StyleContext;
})();