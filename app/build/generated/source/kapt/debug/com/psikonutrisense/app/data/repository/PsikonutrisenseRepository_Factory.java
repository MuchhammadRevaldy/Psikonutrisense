package com.psikonutrisense.app.data.repository;

import com.psikonutrisense.app.data.local.SessionManager;
import com.psikonutrisense.app.data.remote.PsikonutrisenseApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class PsikonutrisenseRepository_Factory implements Factory<PsikonutrisenseRepository> {
  private final Provider<PsikonutrisenseApi> apiProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public PsikonutrisenseRepository_Factory(Provider<PsikonutrisenseApi> apiProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.apiProvider = apiProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public PsikonutrisenseRepository get() {
    return newInstance(apiProvider.get(), sessionManagerProvider.get());
  }

  public static PsikonutrisenseRepository_Factory create(Provider<PsikonutrisenseApi> apiProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new PsikonutrisenseRepository_Factory(apiProvider, sessionManagerProvider);
  }

  public static PsikonutrisenseRepository newInstance(PsikonutrisenseApi api,
      SessionManager sessionManager) {
    return new PsikonutrisenseRepository(api, sessionManager);
  }
}
