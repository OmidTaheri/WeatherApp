package ir.omidtaheri.daggercore.di.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by om on 10/23/2017.
 */


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface WeatherIntercepter {
}
