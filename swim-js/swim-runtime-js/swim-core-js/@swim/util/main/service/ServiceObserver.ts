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

import type {Observer} from "../observable/Observer";
import type {Service} from "./Service";

export interface ServiceObserver<R, S extends Service<R> = Service<R>> extends Observer<S> {
  serviceWillAttachRoot?(root: R, service: S): void;

  serviceDidAttachRoot?(root: R, service: S): void;

  serviceWillDetachRoot?(root: R, service: S): void;

  serviceDidDetachRoot?(root: R, service: S): void;

  serviceWillAttach?(service: S): void;

  serviceDidAttach?(service: S): void;

  serviceWillDetach?(service: S): void;

  serviceDidDetach?(service: S): void;
}
