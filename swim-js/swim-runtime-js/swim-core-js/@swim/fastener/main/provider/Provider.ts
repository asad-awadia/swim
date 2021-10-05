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

import {Mutable, Class, Observable, ObserverType, Service} from "@swim/util";
import {Affinity} from "../fastener/Affinity";
import {FastenerOwner, FastenerInit, FastenerClass, Fastener} from "../fastener/Fastener";

export type ProviderMemberService<O, K extends keyof O> =
  O[K] extends Provider<any, infer S> ? S : never;

export type ProviderService<P extends Provider<any, any>> =
  P extends Provider<any, infer S> ? S : never;

export interface ProviderInit<S = unknown> extends FastenerInit {
  type?: unknown;
  observes?: boolean;

  willInherit?(superFastener: Provider<unknown, S>): void;
  didInherit?(superFastener: Provider<unknown, S>): void;
  willUninherit?(superFastener: Provider<unknown, S>): void;
  didUninherit?(superFastener: Provider<unknown, S>): void;

  willBindSuperFastener?(superFastener: Provider<unknown, S>): void;
  didBindSuperFastener?(superFastener: Provider<unknown, S>): void;
  willUnbindSuperFastener?(superFastener: Provider<unknown, S>): void;
  didUnbindSuperFastener?(superFastener: Provider<unknown, S>): void;

  service?: S;
  createService?(): S;
}

export type ProviderDescriptor<O = unknown, S = unknown, I = {}> = ThisType<Provider<O, S> & I> & ProviderInit<S> & Partial<I>;

export interface ProviderClass<P extends Provider<any, any> = Provider<any, any>> extends FastenerClass<P> {
  create(this: ProviderClass<P>, owner: FastenerOwner<P>, providerName: string): P;

  construct(providerClass: ProviderClass, provider: P | null, owner: FastenerOwner<P>, providerName: string): P;

  extend(this: ProviderClass<P>, classMembers?: {} | null): ProviderClass<P>;

  define<O, S extends Observable, I = {}>(descriptor: {extends: ProviderClass | null, observes: boolean} & ProviderDescriptor<O, S, I & ObserverType<S>>): ProviderClass<Provider<any, S> & I>;
  define<O, S extends Observable>(descriptor: {observes: boolean} & ProviderDescriptor<O, S, ObserverType<S>>): ProviderClass<Provider<any, S>>;
  define<O, S, I = {}>(descriptor: {extends: ProviderClass | null} & ProviderDescriptor<O, S, I>): ProviderClass<Provider<any, S> & I>;
  define<O, S>(descriptor: ProviderDescriptor<O, S>): ProviderClass;

  <O, S extends Observable, I = {}>(descriptor: {extends: ProviderClass | null, observes: boolean} & ProviderDescriptor<O, S, I & ObserverType<S>>): PropertyDecorator;
  <O, S extends Observable>(descriptor: {observes: boolean} & ProviderDescriptor<O, S, ObserverType<S>>): PropertyDecorator;
  <O, S, I = {}>(descriptor: {extends: ProviderClass | null} & ProviderDescriptor<O, S, I>): PropertyDecorator;
  <O, S>(descriptor: ProviderDescriptor<O, S>): PropertyDecorator;
}

export interface Provider<O = unknown, S = unknown> extends Fastener<O> {
  (): S;

  /** @override */
  get familyType(): Class<Provider<any, any>> | null;

  /** @internal @override */
  setInherited(inherited: boolean, superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  willInherit(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  onInherit(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  didInherit(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  willUninherit(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  onUninherit(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  didUninherit(superFastener: Provider<unknown, S>): void;

  /** @override */
  get superFastener(): Provider<unknown, S> | null;

  /** @internal @override */
  getSuperFastener(): Provider<unknown, S> | null;

  /** @protected @override */
  willBindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  onBindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  didBindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  willUnbindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  onUnbindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @protected @override */
  didUnbindSuperFastener(superFastener: Provider<unknown, S>): void;

  /** @internal @override */
  attachSubFastener(subFastener: Provider<unknown, S>): void;

  /** @internal @override */
  detachSubFastener(subFastener: Provider<unknown, S>): void;

  readonly service: S;

  getService(): NonNullable<S>;

  getServiceOr<E>(elseService: E): NonNullable<S> | E;

  /** @internal */
  createService(): S;

  /** @protected @override */
  onMount(): void;

  /** @protected @override */
  onUnmount(): void;

  /** @internal */
  get observes(): boolean | undefined; // optional prototype field
}

export const Provider = (function (_super: typeof Fastener) {
  const Provider = _super.extend() as ProviderClass;

  Object.defineProperty(Provider.prototype, "familyType", {
    get: function (this: Provider): Class<Provider<any, any>> | null {
      return Provider;
    },
    configurable: true,
  });

  Provider.prototype.onInherit = function <S>(this: Provider<unknown, S>, superFastener: Provider<unknown, S>): void {
    (this as Mutable<typeof this>).service = superFastener.service;
  };

  Provider.prototype.onBindSuperFastener = function <S>(this: Provider<unknown, S>, superFastener: Provider<unknown, S>): void {
    if (this.inherits && this.affinity === Affinity.Inherited) {
      this.initAffinity(Affinity.Transient);
    }
    _super.prototype.onBindSuperFastener.call(this, superFastener);
  };

  Provider.prototype.onUnbindSuperFastener = function <S>(this: Provider<unknown, S>, superFastener: Provider<unknown, S>): void {
    _super.prototype.onUnbindSuperFastener.call(this, superFastener);
    if (this.inherits && this.affinity === Affinity.Transient) {
      this.initAffinity(Affinity.Inherited);
    }
  };

  Provider.prototype.getService = function <S>(this: Provider<unknown, S>): NonNullable<S> {
    const service = this.service;
    if (service === void 0 || service === null) {
      throw new TypeError(service + " " + this.name + " service");
    }
    return service as NonNullable<S>;
  };

  Provider.prototype.getServiceOr = function <S, E>(this: Provider<unknown, S>, elseService: E): NonNullable<S> | E {
    let service: S | E = this.service;
    if (service === void 0 || service === null) {
      service = elseService;
    }
    return service as NonNullable<S> | E;
  };

  Provider.prototype.createService = function <S>(this: Provider<unknown, S>): S {
    return this.service;
  };

  Provider.prototype.onMount = function (this: Provider): void {
    _super.prototype.onMount.call(this);
    let service = this.service;
    if (service === void 0 || service === null) {
      service = this.createService();
      (this as Mutable<typeof this>).service = service;
    }
    if (Observable.is(service)) {
      if (!this.inherited && service instanceof Service) {
        service.attachRoot(this.owner);
      }
      if (this.observes !== false) {
        service.observe(this);
      }
    }
  };

  Provider.prototype.onUnmount = function (this: Provider): void {
    const service = this.service;
    if (Observable.is(service)) {
      if (this.observes !== false) {
        service.unobserve(this);
      }
      if (!this.inherited && service instanceof Service) {
        service.detachRoot(this.owner);
      }
    }
    _super.prototype.onUnmount.call(this);
  };

  Provider.construct = function <P extends Provider<any, any>>(providerClass: ProviderClass, provider: P | null, owner: FastenerOwner<P>, providerName: string): P {
    if (provider === null) {
      provider = function Provider(): ProviderService<P> {
        return provider!.service;
      } as P;
      Object.setPrototypeOf(provider, providerClass.prototype);
    }
    provider = _super.construct(providerClass, provider, owner, providerName) as P;
    (provider as Mutable<typeof provider>).service = void 0 as unknown as ProviderService<P>;
    provider.initAffinity(Affinity.Inherited);
    provider.initInherits(true);
    return provider;
  };

  Provider.define = function <O, S>(descriptor: ProviderDescriptor<O, S>): ProviderClass<Provider<any, S>> {
    let superClass = descriptor.extends as ProviderClass | undefined;
    const affinity = descriptor.affinity;
    const inherits = descriptor.inherits;
    const service = descriptor.service;
    delete descriptor.extends;
    delete descriptor.affinity;
    delete descriptor.inherits;
    delete descriptor.service;

    if (superClass === void 0 || superClass === null) {
      superClass = this;
    }

    const providerClass = superClass.extend(descriptor);

    providerClass.construct = function (providerClass: ProviderClass, provider: Provider<O, S> | null, owner: O, providerName: string): Provider<O, S> {
      provider = superClass!.construct(providerClass, provider, owner, providerName);
      if (affinity !== void 0) {
        provider.initAffinity(affinity);
      }
      if (inherits !== void 0) {
        provider.initInherits(inherits);
      }
      if (service !== void 0) {
        (provider as Mutable<typeof provider>).service = service;
      }
      return provider;
    };

    return providerClass;
  };

  return Provider;
})(Fastener);
