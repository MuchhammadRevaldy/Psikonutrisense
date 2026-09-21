package com.psikonutrisense.app.di;

import com.psikonutrisense.app.data.remote.PsikonutrisenseApi;
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
public final class AppModule_ProvidePsikonutrisenseApiFactory implements Factory<PsikonutrisenseApi> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public AppModule_ProvidePsikonutrisenseApiFactory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public PsikonutrisenseApi get() {
    return providePsikonutrisenseApi(okHttpClientProvider.get());
  }

  public static AppModule_ProvidePsikonutrisenseApiFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new AppModule_ProvidePsikonutrisenseApiFactory(okHttpClientProvider);
  }

  public static PsikonutrisenseApi providePsikonutrisenseApi(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePsikonutrisenseApi(okHttpClient));
  }
}
