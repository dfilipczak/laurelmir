(ns symus.laurelmir.rational
  (:refer-clojure :exclude [rational? numerator denominator + - * / *' +' - -']))

(def sum clojure.core/+)
(def product clojure.core/*)
(def difference clojure.core/-)
(def quotient clojure.core//)

(defrecord Ratio [numerator denominator])

(defn numerator [rational]
  (:numerator rational))

(defn denominator [rational]
  (:denominator rational))

#_(defmethod clojure.core/print-method Ratio [v ^java.io.Writer w]
  (clojure.core/print-method 
   (symbol (str "r" (numerator v) "/" (denominator v))) 
   w)) ;;as a symbol, no r

#_(defmethod clojure.pprint/simple-dispatch
  Ratio
  [r] (print (symbol (str "r" (numerator r) "/" (denominator r)))))

(defn rational? [x]
  (= (type x) Ratio))
(comment 

  (symbol "a")
  (type Ratio) 
  (type (rational 1 2))
  (type (rational 1 2))
  )

(defn rational [numerator denominator]
  (->Ratio numerator denominator))

(defn reciprocal [rat]
  (rational (denominator rat) (numerator rat)))

(defn greatest-common-divisor [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn normalize [rat]
  (let [gcd (greatest-common-divisor (numerator rat) (denominator rat))]
    (rational
     (quotient (numerator rat) gcd) 
     (quotient (denominator rat) gcd))))

(defn *' [rata ratb]
  (rational 
   (product (numerator rata) (numerator ratb))
   (product (denominator rata) (denominator ratb))))

(defn * [rata ratb]
  (normalize (*' rata ratb)))

(defn / [rata ratb]
  (* rata (reciprocal ratb)))

(defn +' [rata ratb]
  (rational
   (sum 
    (product (numerator rata) (denominator ratb)) 
    (product (numerator ratb) (denominator rata)))
   (product 
    (denominator ratb) 
    (denominator rata))))

(defn + [rata ratb]
  (normalize (+' rata ratb)))

(defn -' [rata ratb]
  (rational
   (difference
    (product (numerator rata) (denominator ratb))
    (product (numerator ratb) (denominator rata)))
   (product
    (denominator ratb)
    (denominator rata))))

(defn - [rata ratb]
  (normalize (-' rata ratb)))

(defn realize [rat]
  (quotient (numerator rat) (denominator rat)))

(def one (rational 1 1))
(def zero (rational 0 1))