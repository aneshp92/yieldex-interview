package com.yieldex.interview.data.model.postgres

import javax.persistence.*

@Entity
@Table(name="users")
@SequenceGenerator(name="my_seq", sequenceName="users_id_seq", allocationSize = 1)
class User(
        var firstName: String,
        var lastName: String,
        var email: String,
        var password: String,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="my_seq")
        var id: Long
) {
        private constructor() : this("", "", "", "", -1)
}