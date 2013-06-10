package org.highj.data.tuple;

import org.highj.__;
import org.highj.data.tuple.t2.*;
import org.highj.typeclass0.compare.Eq;
import org.highj.typeclass0.compare.Ord;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.comonad.Comonad;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Bind;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

/**
 * A tuple of arity 2, a.k.a. "pair".
 */
public abstract class T2<A, B> implements __<T2.µ, A, B> {
    public static class µ {
    }

    public abstract A _1();

    public abstract B _2();

    public T2<B, A> swap() {
        return Tuple.of(_2(), _1());
    }

    public <AA> T2<AA, B> map_1(Function<? super A, ? extends AA> fn) {
        return Tuple.of(fn.apply(_1()), _2());
    }

    public <BB> T2<A, BB> map_2(Function<? super B, ? extends BB> fn) {
        return Tuple.of(_1(), fn.apply(_2()));
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", _1(), _2());
    }


    @Override
    public int hashCode() {
        return 31 * _1().hashCode() + 37 * _2().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof T2) {
            T2<?, ?> that = (T2) o;
            return this._1().equals(that._1())
                    && this._2().equals(that._2());
        }
        return false;
    }

    public static <A, B> Eq<T2<A, B>> eq(Eq<? super A> eqA, Eq<? super B> eqB) {
        return (one, two) -> eqA.eq(one._1(), two._1())
                && eqB.eq(one._2(), two._2());
    }

    public static <A, B> Ord<T2<A, B>> ord(Ord<? super A> ordA, Ord<? super B> ordB) {
        return (one, two) -> ordA.cmp(one._1(), two._1())
                .andThen(ordB.cmp(one._2(), two._2()));
    }

    public static <S> T2Functor<S> functor() {
        return new T2Functor<S>() {
        };
    }

    public static <S> T2Bind<S> bind(Semigroup<S> semigroupS) {
        return () -> semigroupS;
    }

    public static <S> T2Monad<S> monad(Monoid<S> monoidS) {
        return () -> monoidS;
    }

    public static <S> T2Comonad<S> comonad() {
        return new T2Comonad<S>(){};
    }

    public static <A, B> Semigroup<T2<A, B>> semigroup(Semigroup<A> semigroupA, Semigroup<B> semigroupB) {
        return T2Semigroup.from(semigroupA, semigroupB);
    }

    public static <A, B> Monoid<T2<A, B>> monoid(Monoid<A> monoidA, Monoid<B> monoidB) {
        return T2Monoid.from(monoidA, monoidB);
    }

    public static <A, B> Group<T2<A, B>> group(Group<A> groupA, Group<B> groupB) {
        return T2Group.from(groupA, groupB);
    }

}