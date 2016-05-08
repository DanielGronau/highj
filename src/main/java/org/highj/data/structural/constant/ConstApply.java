package org.highj.data.structural.constant;

import org.derive4j.hkt.__;
import org.highj.data.structural.Const;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.data.structural.Const.narrow;
import static org.highj.data.structural.Const.µ;

public interface ConstApply<S> extends Apply<__<µ,S>>, ConstFunctor<S> {

    public Semigroup<S> getS();

    @Override
    public default <A, B> Const<S, B> ap(__<__<µ, S>, Function<A, B>> fn, __<__<µ, S>, A> nestedA) {
        S s1 = narrow(fn).get();
        S s2 = narrow(nestedA).get();
        return new Const<>(getS().apply(s1, s2));
    }

}
