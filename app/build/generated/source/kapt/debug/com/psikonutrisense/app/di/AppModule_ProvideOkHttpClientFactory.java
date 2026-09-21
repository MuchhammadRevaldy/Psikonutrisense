package com.psikonutrisense.app.di;

import com.psikonutrisense.app.data.local.SessionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<SessionManager> sessionManagerProvider;

  public AppModule_ProvideOkHttpClientFactory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(sessionManagerProvider.get());
  }

  public static AppModule_ProvideOkHttpClientFactory create(
      Provider<SessionManager> sessionManagerProvider) {
    return new AppModule_ProvideOkHttpClientFactory(sessionManagerProvider);
  }

  public static OkHttpClient provideOkHttpClient(SessionManager sessionManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOkHttpClient(sessionManager));
  }
}
