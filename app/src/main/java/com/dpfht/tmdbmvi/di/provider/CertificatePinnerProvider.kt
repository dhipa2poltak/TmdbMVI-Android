package com.dpfht.tmdbmvi.di.provider

import okhttp3.CertificatePinner
import javax.inject.Inject
import javax.inject.Provider

class CertificatePinnerProvider @Inject constructor(

): Provider<CertificatePinner> {
  override fun get(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
      .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
      .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
      .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
      .build()
  }
}
