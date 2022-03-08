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

import {HashCode, Lazy, Strings} from "@swim/util";
import {Output, Format, Debug, Display} from "@swim/codec";
import {Uri} from "./Uri";

/** @public */
export type AnyUriUser = UriUser | UriUserInit | string;

/** @public */
export interface UriUserInit {
  username?: string;
  password?: string;
}

/** @public */
export class UriUser implements HashCode, Debug, Display {
  /** @internal */
  constructor(username: string | undefined, password: string | undefined) {
    this.username = username;
    this.password = password;
  }

  isDefined(): boolean {
    return this.username !== void 0;
  }

  readonly username: string | undefined;

  withUsername(username: string | undefined): UriUser {
    if (username !== this.username) {
      return this.copy(username, this.password);
    } else {
      return this;
    }
  }

  readonly password: string | undefined;

  withPassword(password: string | undefined): UriUser {
    if (password !== this.password) {
      return this.copy(this.username, password);
    } else {
      return this;
    }
  }

  protected copy(username: string | undefined, password: string | undefined): UriUser {
    return UriUser.create(username, password);
  }

  toAny(user?: {username?: string, password?: string}): {username?: string, password?: string} | undefined {
    if (this.username !== void 0) {
      if (user === void 0) {
        user = {};
      }
      user.username = this.username;
      if (this.password !== void 0) {
        user.password = this.password;
      }
    }
    return user;
  }

  equals(that: unknown): boolean {
    if (this === that) {
      return true;
    } else if (that instanceof UriUser) {
      return this.username === that.username && this.password === that.password;
    }
    return false;
  }

  hashCode(): number {
    return Strings.hash(this.toString());
  }

  debug<T>(output: Output<T>): Output<T> {
    output = output.write("UriUser").write(46/*'.'*/);
    if (this.isDefined()) {
      output = output.write("parse").write(40/*'('*/).write(34/*'"'*/)
                     .display(this).write(34/*'"'*/).write(41/*')'*/);
    } else {
      output = output.write("undefined").write(40/*'('*/).write(41/*')'*/);
    }
    return output;
  }

  display<T>(output: Output<T>): Output<T> {
    if (this.username !== void 0) {
      output = Uri.writeUser(output, this.username);
      if (this.password !== void 0) {
        output = output.write(58/*':'*/);
        output = Uri.writeUser(output, this.password);
      }
    }
    return output;
  }

  toString(): string {
    return Format.display(this);
  }

  @Lazy
  static undefined(): UriUser {
    return new UriUser(void 0, void 0);
  }

  static create(username: string | undefined, password?: string | undefined): UriUser {
    if (username !== void 0 || password !== void 0) {
      return new UriUser(username, password);
    } else {
      return UriUser.undefined();
    }
  }

  static fromInit(init: UriUserInit): UriUser {
    return UriUser.create(init.username, init.password);
  }

  static fromAny(value: AnyUriUser | null | undefined): UriUser {
    if (value === void 0 || value === null) {
      return UriUser.undefined();
    } else if (value instanceof UriUser) {
      return value;
    } else if (typeof value === "object") {
      return UriUser.fromInit(value);
    } else if (typeof value === "string") {
      return UriUser.parse(value);
    } else {
      throw new TypeError("" + value);
    }
  }

  static parse(userPart: string): UriUser {
    return Uri.standardParser.parseUserString(userPart);
  }
}
